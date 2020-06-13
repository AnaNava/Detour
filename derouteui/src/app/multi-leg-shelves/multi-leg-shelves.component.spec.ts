import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiLegShelvesComponent } from './multi-leg-shelves.component';

describe('MultiLegShelvesComponent', () => {
  let component: MultiLegShelvesComponent;
  let fixture: ComponentFixture<MultiLegShelvesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultiLegShelvesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiLegShelvesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
