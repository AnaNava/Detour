import { Component, OnInit } from '@angular/core';
import {NgsserviceService} from '../ngsservice.service';
import {Itinerary} from '../Itinerary';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-itineraries',
  templateUrl: './itineraries.component.html',
  styleUrls: ['./itineraries.component.css']
})
export class ItinerariesComponent implements OnInit {

  itinerary: Itinerary = {
    id: 1,
    name: "HNL-LAX-DL-1212-20190515-FIRST-TAVSLOFI"
  };
  itineraryForm: FormGroup;

  itins:Itinerary[]
  response:Response
  selectedItin: Itinerary=this.itinerary;

  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router,private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getItineraries();
    this.itineraryForm = this.formBuilder.group({
      'itinerary_string' : [null, Validators.nullValidator]
    });
    this.itineraryForm.setValue({
      itinerary_string:this.itinerary.name
    });
  }
  getItineraries(): void {
    this.itins = this.api.getItineraries();
   
  }

  onSelect(itin: Itinerary): void {

    this.selectedItin=itin;
    this.itineraryForm.setValue({
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
  this.router.navigate(['/itineraryshelves',this.itineraryForm.get('itinerary_string').value]);
}


}
