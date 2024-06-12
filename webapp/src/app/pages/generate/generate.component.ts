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

import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest, HttpResponse} from "@angular/common/http";
import {downloadFromResponse} from "../../utils/http-download";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NzUploadFile} from "ng-zorro-antd/upload";
import {filter} from "rxjs";
import {DataSourceInfo} from "../../common/datasource.model";

@Component({
  selector: 'app-generate',
  templateUrl: './generate.component.html',
  styleUrls: ['./generate.component.less']
})
export class GenerateComponent implements OnInit {
  datasourceList: DataSourceInfo[] = [];
  schemaList: string[] = [];
  fileList: NzUploadFile[] = [];

  loading = false;
  validateForm: FormGroup;
  uploadAccept = '.ftl';

  get selectedDatasourceId() {
    return this.validateForm.controls['datasourceId'].value;
  }

  get selectedDbSchema() {
    return this.validateForm.controls['dbSchema'].value;
  }

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.validateForm = this.fb.group({
      datasourceId: [null, [Validators.required]],
      dbSchema: [null,],
      tableNames: [[],],
      title: ['数据库表结构文档',],
      description: ['数据库表结构文档',],
      version: ['1.0.0',],
      fileType: ['HTML',],
      produceType: ['freemarker',],
    })
  }

  ngOnInit(): void {
    this.queryDatasourceList();
    this.validateForm.controls['datasourceId'].valueChanges.subscribe(value => {
      const dataSourceInfo = this.datasourceList.filter(x => x.id === value)[0];
      if (dataSourceInfo) {
        this.getSchemaList(value);
        this.validateForm.patchValue({dbSchema: dataSourceInfo.dbSchema});
      }
    });
    this.validateForm.controls['fileType'].valueChanges.subscribe(value => {
      const produceType = this.validateForm.controls['produceType'].value;
      if (value != 'WORD' && produceType == 'poitl') {
        this.validateForm.patchValue({produceType: 'freemarker'});
      }
    });
    this.validateForm.controls['produceType'].valueChanges.subscribe(value => {
      if (value == 'velocity') {
        this.uploadAccept = '.vm';
      } else if (value == 'poitl') {
        this.uploadAccept = '.docx';
      } else {
        this.uploadAccept = '.ftl';
      }
    });
  }

  beforeUpload = (file: NzUploadFile): boolean => {
    this.fileList = [file];
    return false;
  };


  queryDatasourceList() {
    this.http.get('./api/datasource/queryList').subscribe((res: any) => {
      this.datasourceList = res.data;
    })
  }

  getSchemaList(datasourceId: string) {
    this.schemaList = [];
    this.http.get('./api/document/list-schemas?datasourceId=' + datasourceId).subscribe((res: any) => {
      this.schemaList = res.data;
    })
  }

  downloadDefaultTemplate() {
    window.open('./api/document/download-templates');
  }

  submit() {
    const config = this.validateForm.getRawValue();
    const data = new FormData();
    data.append('json', JSON.stringify(config))
    if (this.fileList.length > 0) {
      data.append('template', this.fileList[0] as any);
    }
    this.loading = true;

    const req = new HttpRequest('POST', './api/document/generate', data, {
      reportProgress: true,
      responseType: 'blob',
    });

    this.http
      .request(req)
      .pipe(filter(e => e instanceof HttpResponse))
      .subscribe({
        next: (resp: HttpEvent<unknown>) => {
          this.loading = false;
          downloadFromResponse(resp as any);
        },
        error: error => {
          this.loading = false;
        }
      })
  }
}
