import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WorkerListPage } from './worker-list.page';

describe('WorkerListPage', () => {
  let component: WorkerListPage;
  let fixture: ComponentFixture<WorkerListPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkerListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
