import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProviderPagePage } from './provider-page.page';

describe('ProviderPagePage', () => {
  let component: ProviderPagePage;
  let fixture: ComponentFixture<ProviderPagePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderPagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
