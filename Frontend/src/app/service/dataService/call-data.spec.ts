import { TestBed } from '@angular/core/testing';

import { CallData } from './call-data';

describe('CallData', () => {
  let service: CallData;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CallData);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
