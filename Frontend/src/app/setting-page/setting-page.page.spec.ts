import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SettingPagePage } from './setting-page.page';

describe('SettingPagePage', () => {
  let component: SettingPagePage;
  let fixture: ComponentFixture<SettingPagePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingPagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
