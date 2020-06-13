import { Injectable } from '@angular/core';
import {Itinerary} from './Itinerary';
import { ITINS } from './mockitineraries';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable,throwError,of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import {formatDate} from '@angular/common';
import { stringify } from '@angular/core/src/render3/util';

const endpoint = 'http://rndengsrv04.atpco.org:8582/ngs/shelf';
const pricingendpoint='http://rndengsrv04.atpco.org:8582/admin/ngs/pricing-shelve-owns';
const multilegendpoint='http://rndengsrv04.atpco.org:8582/ngs/portion-shelf'
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    "Accept": "application/json"
  })
};
@Injectable({
  providedIn: 'root'
})
export class NgsserviceService {

  constructor(private http: HttpClient) { }
  private extractData(res: Response) {
    let body = res;
    return body || { };
  }
  getItineraries(): Itinerary[] {
    return ITINS;
  }

  getProducts (itinerary:string): Observable<any> {
    console.log(endpoint);
    console.log(itinerary);
    return this.http.post<any>(endpoint, "{\"fareSource\": \"1S\","+
      "\"itineraries\":[\""+itinerary+"\"]}", httpOptions).pipe(
      tap(flightSegments => console.log(`got back products`)),
      catchError((error: HttpErrorResponse) => {
        let data = {};
        data = {
            reason: error && error.error.reason ? error.error.reason : '',
            status: error.status
        };
        return throwError(error);
    })
    );
    
  }
  getShelvesforMultiLeg (itinerary:string): Observable<any> {
    console.log(endpoint);
    console.log(itinerary);
    return this.http.post<any>(multilegendpoint, "{\"fareSource\": \"1S\","+
      "\"itineraryPortions\":[\""+itinerary+"\"]}", httpOptions).pipe(
      tap(flightSegments => console.log(`got back products`)),
      catchError((error: HttpErrorResponse) => {
        let data = {};
        data = {
            reason: error && error.error.reason ? error.error.reason : '',
            status: error.status
        };
        return throwError(error);
    })
    );
  }

  getshelvesforitinerary(itinerary:Itinerary)
  {
    console.log(itinerary);
    console.log("{\"itineraries\":[\"SFO-MIA-AA-2548-20191212-FIRST-*\"]}");
    console.log(httpOptions);
    console.log(endpoint);
    return this.http.post<any>(endpoint ,"{\"fareSource\": \"1S\",\"itineraries\":[\"SFO-MIA-AA-2548-20200212-FIRST-*\"]}", httpOptions).pipe(
      tap((product) => console.log(`requested for shelves data `),
      catchError(this.handleError<any>('requestshelves'))
    ));
  }

  getpricingforitineraries(origin:string,destination:string)
  {
    const format = 'yyyy-MM-dd';
    var ticketDate = new Date();
    const locale = 'en-US';
    var ticketDateStr = formatDate(ticketDate.setDate(ticketDate.getDate() + 2), format, locale);
    var travelDateStr=formatDate(ticketDate.setDate(ticketDate.getDate() + 45), format, locale)
    const query:String="{ "+
   " \"fareSource\": \"1S\","+
                  " \"fareClasses\": [],  \"carriers\": [],"+     
                       " \"ticketDate\": \""+ticketDateStr+"\","+     
                       " \"travelStartDate\": \""+travelDateStr+"\","+     
                       " \"origin\": \""+origin+"\",     \"destination\": \""+destination+"\" }";
    console.log(query);
    console.log(httpOptions);
    console.log(endpoint);
    return this.http.post<any>(pricingendpoint ,query, httpOptions).pipe(
      tap((product) => console.log(`requested for pricing shelves data `),
      catchError(this.handleError<any>('requestpricing shelves'))
    ));
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
  
}
