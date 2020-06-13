import { Component, OnInit,ViewEncapsulation } from '@angular/core';
import {NgsserviceService} from '../ngsservice.service';
import {Itinerary} from '../Itinerary';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-multi-leg',
  templateUrl: './multi-leg.component.html',
  styleUrls: ['./itineraries.component.css']
})
export class MultiLegComponent implements OnInit {

  itinerary: Itinerary = {
    id: 1,
    name: "HNL-LAX-DL-1212-20190915-FIRST-TAVSLOFI"
  };
  multiLegForm: FormGroup;

  itins:Itinerary[]
  response:Response
  selectedItin: Itinerary=this.itinerary;

  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router,private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getItineraries();
    this.multiLegForm = this.formBuilder.group({
      'itinerary_string' : [null, Validators.nullValidator]
    });
    this.multiLegForm.setValue({
      itinerary_string:this.itinerary.name
    });
  }
  getItineraries(): void {
    this.itins = this.api.getItineraries();
   
  }

  onSelect(itin: Itinerary): void {

    this.selectedItin=itin;
    this.multiLegForm.setValue({
      itinerary_string: itin.name
    });
}

getshelvesforitinerary() {
  this.api.getshelvesforitinerary(this.itinerary)
  .subscribe(result => {
    this.response=result;
  });
}

onFormSubmit(form:NgForm) {
  this.router.navigate(['/multileg',this.multiLegForm.get('itinerary_string').value]);
}


}

