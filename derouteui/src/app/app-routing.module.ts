import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShelvesDetailComponent } from './shelves-detail/shelves-detail.component';
import {ItinerariesComponent} from './itineraries/itineraries.component';
import { PricingShelvesComponent } from './pricingshelves/pricingshelves.component';
import {PricingComponent} from './pricing/pricing.component';
import {MultiLegShelvesComponent} from './multi-leg-shelves/multi-leg-shelves.component';
import {MultiLegComponent} from './multi-leg/multi-leg.component';
import { PricingShelvesComponent2 } from './pricingshelves2/pricingshelves.2.component';
import { LoginUserComponent } from './login-user/login-user.component';
import {LandingComponent} from './landing/landing.component';

const routes: Routes = [
  {
    path: 'itineraryshelves/:itinerary',
    component: ShelvesDetailComponent,
    data: { title: 'List of Shelves' }
  },
  {//TODO: This has been changed to use a new compponent tempo...
    path: 'pricingitinshelves/:od',
    component: PricingShelvesComponent,
    data: { title: 'List of Pricing Shelves' }
  },
  {
    path: 'multileg/:itinerary',
    component: MultiLegShelvesComponent,
    data: { title: 'List of Portion Shelves' }
  },
  {
    path: 'pricing',
    component: PricingComponent,
    data: { title: 'Query Pricing' }
  },
  {
    path: 'itinerary',
    component: ItinerariesComponent,
    data: { title: 'Query Itinerary' }
  },
  {
    path: 'multilegshelves',
    component: MultiLegComponent,
    data: { title: 'Query MultiLeg' }
  },
  {
    path: 'login',
    component: LoginUserComponent,
    data: { title: 'Login' }
  },
  {
    path: 'landing',
    component: LandingComponent,
    data: { title: 'Landing' }
  },
  {
    path: '',
    redirectTo: '/landing',
    pathMatch: 'full'
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
