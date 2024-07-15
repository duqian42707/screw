import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TableSelectorComponent} from "./table-selector.component";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzTransferModule} from "ng-zorro-antd/transfer";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {FormsModule} from "@angular/forms";


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
    NzInputModule,
    NzDividerModule,
    FormsModule,
  ]
})
export class TableSelectorModule {
}
