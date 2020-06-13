import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {NgsserviceService} from '../ngsservice.service';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.css']
})
export class PricingComponent implements OnInit {

  pricingForm: FormGroup;
  constructor(private route: ActivatedRoute, private api: NgsserviceService, private router: Router,private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.pricingForm = this.formBuilder.group({
      'origin' : [null, Validators.nullValidator],
      'destination' : [null, Validators.nullValidator]
  });

}

onFormSubmit(form:NgForm) {
  this.router.navigate(['/pricingitinshelves/',this.pricingForm.get('origin').value+this.pricingForm.get('destination').value]);
}
}
