import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardControlPage } from './dashboard-control.page';

describe('DashboardControlPage', () => {
  let component: DashboardControlPage;
  let fixture: ComponentFixture<DashboardControlPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardControlPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
