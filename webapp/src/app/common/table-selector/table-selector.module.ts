import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TableSelectorComponent} from "./table-selector.component";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzTransferModule} from "ng-zorro-antd/transfer";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";


@NgModule({
  declarations: [TableSelectorComponent],
  exports: [
    TableSelectorComponent
  ],
  imports: [
    CommonModule,
    NzButtonModule,
    NzModalModule,
    NzTransferModule,
    NzTableModule,
    NzToolTipModule,
  ]
})
export class TableSelectorModule {
}
