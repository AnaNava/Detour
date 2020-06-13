import { TestBed } from '@angular/core/testing';

import { NgsserviceService } from './ngsservice.service';

describe('NgsserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NgsserviceService = TestBed.get(NgsserviceService);
    expect(service).toBeTruthy();
  });
});
