import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShelvesDetailComponent } from './shelves-detail.component';

describe('ShelvesDetailComponent', () => {
  let component: ShelvesDetailComponent;
  let fixture: ComponentFixture<ShelvesDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShelvesDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShelvesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
