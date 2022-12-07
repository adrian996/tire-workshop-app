import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

const baseUrl = 'http://localhost:8082/api/v1/london';

class TireChangeBookingResponse{
  uuid: number;
  time: Date;
}

@Injectable({
  providedIn: 'root'
})
export class LondonWorkshopService {

  constructor(private http: HttpClient) { }

  getAllTimes(): Observable<any> {
    return this.http.get(`${baseUrl}/times`, {responseType: 'text'});
  }

  getByDate(from: any, until: any): Observable<any> {
    return this.http.get(`${baseUrl}/times?from=${from}&until=${until}`, {responseType: 'text'});
  }

  bookTime(data: any, uuid: number): Observable<any> {
      return this.http.put(`${baseUrl}/${uuid}/booking`, data, {responseType: 'text'});
  }
}
