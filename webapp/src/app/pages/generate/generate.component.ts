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
  fileList: NzUploadFile[] = [];

  loading = false;
  validateForm: FormGroup;
  uploadAccept = '.ftl';

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.validateForm = this.fb.group({
      datasourceId: [null, [Validators.required]],
      dbSchema: [null,],
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
    this.http.get('/api/datasource/queryList').subscribe((res: any) => {
      this.datasourceList = res.data;
    })
  }

  downloadDefaultTemplate() {
    window.open('/api/document/download-templates');
  }

  submit() {
    const config = this.validateForm.getRawValue();
    const data = new FormData();
    data.append('json', JSON.stringify(config))
    if (this.fileList.length > 0) {
      data.append('template', this.fileList[0] as any);
    }
    this.loading = true;

    const req = new HttpRequest('POST', '/api/document/generate', data, {
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
