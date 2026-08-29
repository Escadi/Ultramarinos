import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TPVPage } from './tpv.page';

describe('TPVPage', () => {
  let component: TPVPage;
  let fixture: ComponentFixture<TPVPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(TPVPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
