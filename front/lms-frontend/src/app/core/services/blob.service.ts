import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BlobService {

  constructor(private http: HttpClient) { }

  downloadPdf(byteArray: Uint8Array, fileName: string = 'file.pdf'): void {
    const blob = new Blob([byteArray], { type: 'application/pdf' });
    this.saveFile(blob, fileName);
  }

  downloadXlsx(byteArray: Uint8Array, fileName: string = 'file.xlsx'): void {
    const blob = new Blob([byteArray], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    this.saveFile(blob, fileName);
  }

  private saveFile(blob: Blob, fileName: string): void {
    const link = document.createElement('a');
    const url = window.URL.createObjectURL(blob);
    link.href = url;
    link.download = fileName;
    link.click();
    window.URL.revokeObjectURL(url);
  }
}
