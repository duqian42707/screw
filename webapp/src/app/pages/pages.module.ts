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

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GenerateComponent} from "./generate/generate.component";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzFormModule} from "ng-zorro-antd/form";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzSpinModule} from "ng-zorro-antd/spin";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzUploadModule} from "ng-zorro-antd/upload";
import {DatasourceComponent} from './datasource/datasource.component';
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzMessageModule} from "ng-zorro-antd/message";
import {TableSelectorModule} from "../common/table-selector/table-selector.module";
import { DocsComponent } from './docs/docs.component';
import {MarkdownModule} from "ngx-markdown";


@NgModule({
  declarations: [GenerateComponent, DatasourceComponent, DocsComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzButtonModule,
    NzFormModule,
    NzInputModule,
    NzSpinModule,
    NzRadioModule,
    NzSelectModule,
    NzUploadModule,
    NzModalModule,
    NzDividerModule,
    NzPopconfirmModule,
    NzTableModule,
    NzMessageModule,
    TableSelectorModule,
    MarkdownModule.forRoot()
  ]
})
export class PagesModule {
}
