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

import {HttpResponse} from "@angular/common/http";

/**
 * 根据http请求的返回值进行下载
 *
 */
export function downloadFromResponse(resp: HttpResponse<Blob>): void {
  let filename = '数据导出.xlsx';
  // 尝试从响应头中解析出文件名
  const disposition = resp.headers.get('content-disposition');
  if (disposition) {
    const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    const matches = filenameRegex.exec(disposition);
    if (matches != null && matches[1]) {
      filename = matches[1].replace(/['"]/g, '');
      filename = decodeURIComponent(filename);
    }
  }
  if (resp.body) {
    const blob = new Blob([resp.body]);
    const a = document.createElement('a');
    document.body.appendChild(a);
    a.download = filename;
    a.href = URL.createObjectURL(blob);
    a.click();
  }
}
