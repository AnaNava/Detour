import { BrowserModule } from '@angular/platform-browser';
import { FormsModule , ReactiveFormsModule } from '@angular/forms'; // <-- NgModel lives here
import { NgModule } from '@angular/core';
import {HttpModule} from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './materialmodule';
import 'hammerjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ItinerariesComponent } from './itineraries/itineraries.component';
import { ShelvesDetailComponent } from './shelves-detail/shelves-detail.component';
import { PricingComponent } from './pricing/pricing.component';
import { PricingShelvesComponent } from './pricingshelves/pricingshelves.component';
import { PricingShelvesComponent2 } from './pricingshelves2/pricingshelves.2.component';
import { MultiLegComponent } from './multi-leg/multi-leg.component';
import { MultiLegShelvesComponent } from './multi-leg-shelves/multi-leg-shelves.component';
import { LoginUserComponent } from './login-user/login-user.component';
import { LandingComponent } from './landing/landing.component';

@NgModule({
  declarations: [
    AppComponent,
    ItinerariesComponent,
    ShelvesDetailComponent,
    PricingComponent,
    PricingShelvesComponent,
    PricingShelvesComponent2,
    MultiLegComponent,
    MultiLegShelvesComponent,
    LoginUserComponent,
    LandingComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
