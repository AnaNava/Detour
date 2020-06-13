import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiLegComponent } from './multi-leg.component';

describe('MultiLegComponent', () => {
  let component: MultiLegComponent;
  let fixture: ComponentFixture<MultiLegComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultiLegComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiLegComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
