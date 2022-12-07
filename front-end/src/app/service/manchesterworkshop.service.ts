import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const baseUrl = 'http://localhost:8081/api/v1/manchester';

class TireChangeBookingResponse{
  id: number;
  time: Date;
  available: boolean
}

@Injectable({
  providedIn: 'root'
})
export class ManchesterworkshopService {

  constructor(private http: HttpClient) { }

  getAllTimes(): Observable<any[]> {
    return this.http.get<any>(`${baseUrl}/times`);
  }

  getByDate(from: any): Observable<any[]> {
    return this.http.get<any>(`${baseUrl}/times?from=${from}`);
  }

  bookTime(data: any, id: number): Observable<any> {
      return this.http.post(`${baseUrl}/${id}/booking`, data);
  }
}
