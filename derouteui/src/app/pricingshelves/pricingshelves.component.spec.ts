import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricingshelvesComponent } from './pricingshelves.component';

describe('PricingshelvesComponent', () => {
  let component: PricingshelvesComponent;
  let fixture: ComponentFixture<PricingshelvesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricingshelvesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricingshelvesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
