export interface DataSourceInfo {
  id: string;
  name: string;
  dbUrl?: string;
  dbUsername?: string;
  dbPassword?: string;
  dbSchema?: string;
  remark?: string;
  createdDate?: Date;
  lastModifiedDate?: Date;
}
