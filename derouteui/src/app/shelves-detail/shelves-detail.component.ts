import { Component, OnInit } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { NgsserviceService} from '../ngsservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-products',
  templateUrl: './shelves-detail.component.html',
  styleUrls: ['./shelves-detail.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})


export class ShelvesDetailComponent implements OnInit {

  displayedColumns: string[] = ['itinerary_name','leg','Star1','Star2','Star3','Star4','Star5','Star6','Other'];
  dataSource: MatTableDataSource<any>;
  response:Object[]=[];
  itineraryshelves:Object[]=[];
  flightshelves:Object[]=[];
  isLoadingResults = true;
  itinerary=this.route.snapshot.params['itinerary'];
  isSelectedRow=false;
  selectedRow:Object;
  flightshelvesdetail:Object[]=[];
  origin:string='';
  destination:string='';

  

  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router) { 
  
  }

  ngOnInit() {
    this.origin=this.getOrigin();
    this.destination=this.getDestination();
    this.api.getProducts(this.itinerary)
      .subscribe(res => {
        this.response = res as Object [];
        console.log(this.response);
        this.itineraryshelves=res["itineraryShelves"];
        console.log(this.itineraryshelves);
        this.flightshelves=this.itineraryshelves[0]["flightShelves"];
        console.log(this.flightshelves);
        this.flightshelves.forEach(element => this.flightshelvesdetail.push(element,{ detailRow: true, element }));
        console.log(this.flightshelvesdetail);
        this.dataSource = new MatTableDataSource(this.flightshelvesdetail );
        this.isLoadingResults = false;
      }, err => {
        console.log(err);

        this.isLoadingResults = false;
      });
    
    }


    toggleChild(flightIndex:string)
    {
      const foundElement = this.dataSource.data.find(elem => elem.element !== undefined && elem.element.flightIndex === flightIndex)    
      //console.log("The found element is " + JSON.stringify(foundElement));
      const index = this.dataSource.data.indexOf(foundElement);
      this.dataSource.data[index].detailRow= ! this.dataSource.data[index].detailRow;

    }

    isExpansionDetailRow = (i: number, row: Object) => {
      console.log(row);
      console.log(row.hasOwnProperty('detailRow'));
      return row.hasOwnProperty('detailRow');
    } 

    getSelectedRow()
    {
      return this.selectedRow;
    }

    getOrigin()
    {
       var str=this.itinerary.split("-");
      return str[0]
    }

    getDestination()
    {
      var str=this.itinerary.split("-");
      return (str[str.length-6]);
    }

}
