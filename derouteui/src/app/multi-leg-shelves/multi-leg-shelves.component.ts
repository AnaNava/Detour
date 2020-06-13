import { Component, OnInit } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { NgsserviceService} from '../ngsservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-multi-leg-shelves',
  templateUrl: './multi-leg-shelves.component.html',
  styleUrls: ['./multi-leg-shelves.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
    trigger('detailDrawerExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})


export class MultiLegShelvesComponent implements OnInit {

  displayedColumns: string[] = ['itinerary_name','leg','Star1','Star2','Star3','Star4','Star5','Star6','Other'];
  displayedDrawerColumns: string[] = ['itinerary_name','leg','detailStar1','detailStar2','detailStar3','detailStar4','detailStar5','detailStar6','detailOther','DrawerDetails'];
  dataSource: MatTableDataSource<any>;
  response:Object[]=[];
  itineraryshelves:Object[]=[];
  isLoadingResults = true;
  itinerary=this.route.snapshot.params['itinerary'];
  isSelectedRow=false;
  selectedRow:Object;
  itineraryshelvesdetail:Object[]=[];
  flightshelvesdetail:Object[]=[];
  origin:string='';
  destination:string='';

  

  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router) { 
  
  }

  ngOnInit() {
    this.origin=this.getOrigin();
    this.destination=this.getDestination();
    this.api.getShelvesforMultiLeg(this.itinerary)
      .subscribe(res => {
        this.response = res as Object [];
        console.log(this.response);
        this.itineraryshelves=res["itineraryPortionShelves"];
        console.log(this.itineraryshelves);
        this.itineraryshelves.forEach(element => this.itineraryshelvesdetail.push(element,{ detailRow: true, element }));
        console.log(this.itineraryshelvesdetail);
        this.dataSource = new MatTableDataSource(this.itineraryshelvesdetail);
        this.isLoadingResults = false;
      }, err => {
        console.log(err);

        this.isLoadingResults = false;
      });
    
    }


    toggleChild(flightIndex:string)
    {
      const foundElement = this.dataSource.data.find(elem => elem.element !== undefined && elem.element.itineraryPortionIndex === flightIndex)    
      console.log("the index is"+flightIndex);
      console.log("The found element is " + JSON.stringify(foundElement));
      const index = this.dataSource.data.indexOf(foundElement);
      this.dataSource.data[index].detailRow= ! this.dataSource.data[index].detailRow;

    }

    toggleChildDrawer(flightIndex:string)
    {
      const foundElement = this.dataSource.data.find(elem => elem.element !== undefined && elem.element.flightShelves[flightIndex].flightIndex === flightIndex)    
      console.log("the index is"+flightIndex);
      console.log("The found element is " + JSON.stringify(foundElement));
      const index = this.dataSource.data.indexOf(foundElement);
      this.dataSource.data[index].detailRow= ! this.dataSource.data[index].detailRow;

    }

    isExpansionDetailRow = (i: number, row: Object) => {
      console.log(row);
      console.log(row.hasOwnProperty('detailRow'));
      return row.hasOwnProperty('detailRow');
    } 

    isExpansionDetailRowDrawer = (i: number, row: Object) => {
      console.log(row);
      console.log(row.hasOwnProperty('detailRow'));
      return !row.hasOwnProperty('detailRow');
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

