import { Component, OnInit } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { NgsserviceService } from '../ngsservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatTableDataSource } from '@angular/material';
import { getDOM } from '@angular/platform-browser/src/dom/dom_adapter';
import { ActivatedRouteSnapshot } from '@angular/router';
import { DataSource } from '@angular/cdk/table';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-products2',
  templateUrl: './pricingshelves.2.component.html',
  styleUrls: ['./pricingshelves.2.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})


export class PricingShelvesComponent2 implements OnInit {

  displayedColumns: string[] = ['itinerary_name', 'leg', 'Star1', 'Star2', 'Star3', 'Star4', 'Star5', 'Star6', 'Other'];
  drawerColumns: string[] = ['DrawerName', 'Shelf1', 'Shelf2', 'Shelf3', 'Shelf4', 'Shelf5', 'Shelf6', 'Other']
  dataSource: MatTableDataSource<any>;
  drawerdataSource: MatTableDataSource<any>;
  response: Object[] = [];
  itineraryshelves: Object[] = [];
  flightshelves: Object[] = [];
  isLoadingResults = true;
  origindestination = this.route.snapshot.params['od'];
  isSelectedRow = false;
  selectedRow: Object;
  itineraryshelvesdetail: Object[] = [];
  drawersdetail: Object[] = [];
  origin: string = '';
  destination: string = '';

  SHELFS = [{
    short: 'one',
    upper: 'ONE',
    cabin: 'ECON'
  },
  {
    short: 'two',
    upper: 'TWO',
    cabin: 'ECONPLUS'
  },
  {
    short: 'three',
    upper: 'THREE',
    cabin: 'PREMECON'
  },
  {
    short: 'four',
    upper: 'FOUR',
    cabin: 'BUSINESS'
  },
  {
    short: 'five',
    upper: 'FIVE',
    cabin: 'FIRST'
  },
  {
    short: 'six',
    upper: 'SIX',
    cabin: 'FIRSTPLUS'
  },
  {
    short: 'other',
    upper: 'OTHER',
    cabin: ''
  }];

  DRAWERS = ['advanceChange','beverageAlcoholicCost','carryOnAllowance']

  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router) {
  }

  ngOnInit() {
    this.getod();
    this.api.getpricingforitineraries(this.origin, this.destination)
      .subscribe(res => {
        this.response = res as Object[];
        console.log(this.response);
        this.itineraryshelves = res["shelvedItineraries"];
        console.log(this.itineraryshelves);
        this.itineraryshelves.forEach(element => this.itineraryshelvesdetail.push(element, { detailRow: true, element }));
        console.log(this.itineraryshelvesdetail);
        this.dataSource = new MatTableDataSource(this.itineraryshelvesdetail);
        this.isLoadingResults = false;
      }, err => {
        console.log(err);

        this.isLoadingResults = false;
      });

  }


  getod() {
    this.origin = this.origindestination.substring(0, 3);
    this.destination = this.origindestination.substring(3, 6);
  }

  toggleDetail(detail){
    detail.expanded = !(detail.expanded && detail.expanded===true);
  }
  toggleChild(flightIndex: object) {
    const foundElement = this.dataSource.data.find(elem => elem.element !== undefined && elem.element.itineraryFlight === flightIndex)
    //console.log("The found element is " + JSON.stringify(foundElement));
    const index = this.dataSource.data.indexOf(foundElement);
    this.dataSource.data[index].detailRow = !this.dataSource.data[index].detailRow;

  }

  isExpansionDetailRow = (i: number, row: Object) => {
    console.log(row);
    console.log(row.hasOwnProperty('detailRow'));
    return row.hasOwnProperty('detailRow');
  }

  getSelectedRow() {
    return this.selectedRow;
  }

  isEmptyObject(obj: Object) {
    return (obj !== undefined);

  }

  isEmptyObjectnew(obj: Object) {
    console.log(obj);
    return (obj !== undefined);

  }

  isImgSrc(str: String, type: String) {
    if (str == null || str == "") {
      if (type == "ECON") {
        return 'assets/econ1.PNG';
      }
      if (type == "ECONPLUS") {
        return 'assets/econplus.png';
      }
      if (type == "PREMECON") {
        return 'assets/econ3.png';
      }
      if (type == "BUSINESS") {
        return 'assets/business.PNG';
      }
      if (type == "FIRST") {
        return 'assets/first.png';
      }
      if (type == "FIRSTPLUS") {
        return 'assets/firstplus.png';
      }
    }
    else {
      return str;
    }
  }

  getimageforcarrier(str: String) {
    if (str == 'AA') {
      return 'assets/american.png';
    }
    if (str == 'DL') {
      return 'assets/delta.png';
    }
    if (str == 'AS') {
      return 'assets/alaska.jpg';
    }
    if (str == 'UA') {
      return 'assets/united.png';
    }
    if (str == 'VS') {
      return 'assets/virgin.png';
    }
    if (str == 'BA') {
      return 'assets/BA.png';
    }
    if (str == 'AF') {
      return 'assets/AF.png';
    }
    if (str == 'IB') {
      return 'assets/IB.png';
    }
    if (str == 'LH') {
      return 'assets/LH.png';
    }
  }


}
