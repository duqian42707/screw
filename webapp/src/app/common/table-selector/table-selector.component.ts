import {Component, EventEmitter, forwardRef, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {TransferItem} from "ng-zorro-antd/transfer";
import {environment} from "../../../environments/environment";
import {NzModalService} from "ng-zorro-antd/modal";

@Component({
  selector: 'app-table-selector',
  templateUrl: './table-selector.component.html',
  styleUrls: ['./table-selector.component.less'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TableSelectorComponent),
    multi: true
  }]
})
export class TableSelectorComponent implements OnInit, ControlValueAccessor {
  @Input() datasourceId: string = '';
  @Input() dbSchema: string = '';
  @Output() screwOnOk = new EventEmitter<string[]>();
  private innerValue: string[] = [];
  list: TransferItem[] = [];
  disabled = false;
  loading = false;
  // 控制弹框的一些属性
  modal = {
    visible: false,
  };

  editTableModal = {
    tableNames: ''
  }

  $asTransferItems = (data: unknown): TransferItem[] => data as TransferItem[];

  filterOption(inputValue: string, item: any): boolean {
    return item.title.toLowerCase().includes(inputValue.toLowerCase()) || (item['remarks'] || '').toLowerCase().includes(inputValue.toLowerCase());
  }

  private onTouchedCallback: any = () => void 0;
  private onChangeCallback: any = () => void 0;

  get selectedCount() {
    return this.value.length;
  }


  get value(): string[] {
    console.debug('get value:', this.innerValue);
    return this.innerValue;
  }

  set value(v: string[]) {
    console.debug('set value:', v);
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChangeCallback(v);
    }
  }

  @ViewChild("editTablesTpl") editTablesTpl!: TemplateRef<any>;

  constructor(private http: HttpClient, private modalService: NzModalService) {
  }

  ngOnInit(): void {
  }

  // ------------------------------------------------------------------------
  // | Control value accessor implements
  // | 参考：https://www.jianshu.com/p/6d5a4e6af0c1
  // ------------------------------------------------------------------------

  registerOnChange(fn: any): void {
    this.onChangeCallback = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedCallback = fn;
  }

  writeValue(obj: any): void {
    console.debug('write value:', obj);
    if (this.innerValue !== obj) {
      this.innerValue = obj;
    }
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  // Control value accessor implements end.

  // 打开弹框
  openTransferModal(): void {
    this.modal.visible = true;
    this.getTableList();
  }


  // 选好
  selectOk(): void {
    const rightList = this.list.filter(x => x.direction === 'right');
    this.value = rightList.map(x => x.title);
    this.screwOnOk.emit(this.value);
    this.closeModal();
  }


  // 关闭弹框
  closeModal(): void {
    this.modal.visible = false;
  }

  openEditModal(): void {
    this.editTableModal.tableNames = this.value.join('\n')
    this.modalService.create({
      nzTitle: '编辑',
      nzContent: this.editTablesTpl,
      nzOnOk: () => {
        this.value = this.editTableModal.tableNames.split('\n');
        this.screwOnOk.emit(this.value);
      }
    })
  }


  getTableList() {
    console.debug('this.innerValue: ', this.innerValue)
    this.loading = true;
    this.http.get(`${environment.apiPrefix}/api/document/list-tables?datasourceId=${this.datasourceId}&dbSchema=${this.dbSchema}`).subscribe((res: any) => {
      this.loading = false;
      this.list = res.data.map((x: any) => {
        return {
          ...x,
          title: x.tableName,
          direction: this.innerValue.includes(x.tableName) ? 'right' : 'left'
        }
      })
    }, error => {
      this.loading = false;
    })
  }

}
