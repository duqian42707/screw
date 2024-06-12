///
/// screw - 简洁好用的数据库表结构文档生成工具
/// Copyright © 2020 SanLi (qinggang.zuo@gmail.com)
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU Lesser General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public License
/// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///

import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {NzMessageService} from "ng-zorro-antd/message";
import {DataSourceInfo} from "../../common/datasource.model";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-datasource',
  templateUrl: './datasource.component.html',
  styleUrls: ['./datasource.component.less']
})
export class DatasourceComponent {
  // 列表加载中状态
  loading = false;
  // 列表数据总条数
  total: number = 0;
  // 列表数据（当前页）
  dataSet: DataSourceInfo[] = [];
  // 编辑弹框
  editModal = {
    // 编辑弹框是否显示
    isVisible: false,
    // 编辑弹框的标题：新增、编辑
    title: '',
    // 编辑弹框保存按钮加载中状态
    saveLoading: false,
  };
  // 编辑弹框中的表单对象
  validateForm: FormGroup;

  constructor(private http: HttpClient,
              private msg: NzMessageService,
              private fb: FormBuilder) {
    // 初始化表单对象，给出需要的字段，和字段的校验规则（如：必填校验、长度校验、正则校验等等）
    this.validateForm = this.fb.group({
      id: [null],
      name: [null, [Validators.required, Validators.maxLength(100)]],
      dbUrl: [null, [Validators.required]],
      dbUsername: [null, [Validators.required]],
      dbPassword: [null, [Validators.required]],
      dbSchema: [null],
      remark: [null],
    });
  }

  ngOnInit() {
    this.queryListForPage();
  }

  /**
   * 加载列表数据
   */
  queryListForPage() {
    this.loading = true;
    this.http.get(`${environment.apiPrefix}/api/datasource/queryList`).subscribe((res: any) => {
      this.loading = false;
      this.dataSet = res.data;
      this.total = res.data.length;
    }, err => {
      this.loading = false;
    });
  }

  /**
   * 打开编辑弹框
   */
  openEditModal(data?: DataSourceInfo) {
    this.editModal.isVisible = true;
    // 重置表单数据
    this.validateForm.reset();
    if (data) {
      this.editModal.title = '编辑';
      this.validateForm.patchValue(data);
    } else {
      this.editModal.title = '新增';
    }
  }


  /**
   * 保存数据（新增、修改）
   */
  submitData() {
    // 表单校验
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsDirty();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    // 表单校验不通过，不进行提交
    if (!this.validateForm.valid) {
      return;
    }
    // 构造表单数据并提交
    const data = this.validateForm.getRawValue();
    let api;
    if (data.id) {
      api = `${environment.apiPrefix}/api/datasource/update`;
    } else {
      api = `${environment.apiPrefix}/api/datasource/insert`;
    }
    this.editModal.saveLoading = true;
    this.http.post(api, data).subscribe((res: any) => {
      // 操作成功，关闭加载中状态，给出提示信息，关闭弹框，重新加载列表数据。
      this.editModal.saveLoading = false;
      this.msg.success(res.msg);
      this.editModal.isVisible = false;
      this.queryListForPage();
    }, err => {
      // 提交失败，关闭加载中状态，无需提示信息（由框架统一处理弹出错误信息），不关闭弹框
      this.editModal.saveLoading = false;
    });
  }

  /**
   * 删除数据
   */
  deleteData(record: any) {
    const id = record.id;
    this.loading = true;
    this.http.post(`${environment.apiPrefix}/api/datasource/delete/${id}`, {}).subscribe((res: any) => {
      // 操作成功，关闭加载中状态，给出提示信息，重新加载列表数据。
      this.loading = false;
      this.msg.success(res.msg);
      this.queryListForPage();
    }, err => {
      // 提交失败，关闭加载中状态，无需提示信息（由框架统一处理弹出错误信息）
      this.loading = false;
    });
  }


}
