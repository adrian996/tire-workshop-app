import { LondonWorkshopService } from './service/londonworkshop.service';
import { ManchesterworkshopService } from './service/manchesterworkshop.service';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import { ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgxXml2jsonService } from 'ngx-xml2json'
import { DatePipe } from '@angular/common';
import { ListKeyManager } from '@angular/cdk/a11y';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';


class TireChangeBookingResponse{
  id: number;
  time: string;
  available: string
}

class TireChangeBookingResponseXML{
  uuid: string;
  time: string;
}

class TireChangeBookingRequest{
  contactInformation: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  vehicles = [
    {type: "car"},
    {type: "truck"}
  ];

  selectedVehicleType: string  ="car";

  workShops = [
    {id: 0, name: "Manchester workshop", address: "14 Bury New Rd, Manchester", vehicles: ["car", "truck"]},
    {id: 1, name: "London workshop", address: "1A Gunton Rd, London", vehicles: ["car"]}
  ];

  currentWorkShop: number;
  currentWorkShopValue: any;
  workShopData: any;
  dataSource: any;
  dataLoaded: boolean = false;
  displayedColumns: string[] = ['time'];
  displayedColumnsXML: string[] = ['time'];
  from: any;
  until: any;
  workShopDataxml: any;
  client: TireChangeBookingRequest = new TireChangeBookingRequest();
  id: number;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private manchesterService: ManchesterworkshopService,
              private londonService: LondonWorkshopService,
              private ngxXml2jsonService: NgxXml2jsonService,
              private matDialog: MatDialog,
              private _snackBar: MatSnackBar){}

  ngOnInit(): void {
    if(this.currentWorkShop == 0)
      this.getAllManchester();
    if(this.currentWorkShop == 1)
      this.getAllLondon();
  }

  /* Method calls the correct tire workshop service and
  * books the tire change time
  */
  bookTime(){
    if(this.currentWorkShop == 0){
      this.manchesterService.bookTime(this.client, this.id).subscribe({
        next: (res) => {
          console.log(res);
          this.openSnackBar("Time booked for " +
            this.client.contactInformation + " on " + res.time);
          this.matDialog.closeAll();
          this.ngOnInit();
        },
        error: (e) => console.error(e),
      });
    }
    if(this.currentWorkShop == 1){
      console.log(this.id);
      this.londonService.bookTime(this.client, this.id).subscribe({
        next: (res) => {
          this.openSnackBar("Time booked for " +
          this.client.contactInformation);
          this.matDialog.closeAll();
          this.ngOnInit();
        },
        error: (e) => console.error(e),
      });
    }
  }

  /* Method finds the correct times from the
  * correct tire workshop APIs
  */
  findTimes(){
    this.currentWorkShop = this.currentWorkShopValue;
    if(this.currentWorkShop == 0 && (this.from == null || this.until == null)){
      console.log(this.currentWorkShop + " getting manchester times");
      this.getAllManchester();
    }
    if(this.currentWorkShop == 1 && (this.from == null || this.until == null)){
      this.getAllLondon();
      console.log(this.currentWorkShop + " getting london times");
    }
    if(this.currentWorkShop == 0 && (this.from != null)){
      this.getManchesterTimesByDate(new DatePipe("en-US").transform(this.from, 'yyyy-MM-dd'));
    }
    if(this.currentWorkShop == 1 && (this.from != null && this.until != null)){
      this.getLondonTimesByDate(new DatePipe("en-US").transform(this.from, 'yyyy-MM-dd'), new DatePipe("en-US").transform(this.until, 'yyyy-MM-dd'));
    }
  }

  /*  Method retrieves all times from the london tire workshop
  *   and initializes MatTable datasource for displaying on the screen
  */
  getAllLondon(){
    {
      this.londonService.getAllTimes().subscribe((data: any) =>{

        const parser = new DOMParser();
        const xml = parser.parseFromString(data, 'text/xml');
        this.workShopData = this.ngxXml2jsonService.xmlToJson(xml);

        this.dataSource = new MatTableDataSource<TireChangeBookingResponseXML>(this.workShopData.List.item);
        this.dataSource.paginator = this.paginator;
        this.dataLoaded = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      })
    }
  }

  /*  Method retrieves all times from the manchester tire workshop
  *   and initializes MatTable datasource for displaying on the screen
  */
  getAllManchester(){
    {
      this.manchesterService.getAllTimes().subscribe((data: TireChangeBookingResponse[]) =>{
        this.workShopData = data;
        this.dataSource = new MatTableDataSource<TireChangeBookingResponse[]>(this.workShopData);
        this.dataSource.paginator = this.paginator;
        this.dataLoaded = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      })
    }
  }

  /*  Method retrieves tire change times from the london tire workshop
  *   with the "from" and "until" variables
  *   and initializes MatTable datasource for displaying on the screen
  */
  getLondonTimesByDate(from: any, until: any){
    {
      this.londonService.getByDate(from, until).subscribe((data: any) =>{
        const parser = new DOMParser();
        const xml = parser.parseFromString(data, 'text/xml');
        this.workShopData = this.ngxXml2jsonService.xmlToJson(xml);

        this.dataSource = new MatTableDataSource<TireChangeBookingResponseXML[]>(this.workShopData.List.item);
        this.dataSource.paginator = this.paginator;
        this.dataLoaded = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      })
    }
  }

  /*  Method retrieves tire change times from the manchester tire workshop
  *   with the from date variable
  *   and initializes MatTable datasource for displaying on the screen
  */
  getManchesterTimesByDate(from: any){
    {
      this.manchesterService.getByDate(from).subscribe((data: TireChangeBookingResponse[]) =>{
        this.workShopData = data;
        this.dataSource = new MatTableDataSource<TireChangeBookingResponse[]>(this.workShopData);
        this.dataSource.paginator = this.paginator;
        this.dataLoaded = true;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      })
    }
  }


  openDialog(templateRef, id) {
    console.log(id + " inside opendialog");
    this.id = id;
    let dialogRef = this.matDialog.open(templateRef, {
     width: '300px'
   });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message);
  }
}
