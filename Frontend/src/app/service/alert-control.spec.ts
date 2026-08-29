import { TestBed } from '@angular/core/testing';

import { AlertControl } from './alert-control';

describe('AlertControl', () => {
  let service: AlertControl;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlertControl);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
