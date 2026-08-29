import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProviderListPage } from './provider-list.page';

describe('ProviderListPage', () => {
  let component: ProviderListPage;
  let fixture: ComponentFixture<ProviderListPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
