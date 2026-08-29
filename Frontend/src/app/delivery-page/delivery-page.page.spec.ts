import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DeliveryPagePage } from './delivery-page.page';

describe('DeliveryPagePage', () => {
  let component: DeliveryPagePage;
  let fixture: ComponentFixture<DeliveryPagePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryPagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
