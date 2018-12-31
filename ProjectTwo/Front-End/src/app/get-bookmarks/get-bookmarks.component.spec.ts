import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetBookmarksComponent } from './get-bookmarks.component';

describe('GetBookmarksComponent', () => {
  let component: GetBookmarksComponent;
  let fixture: ComponentFixture<GetBookmarksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetBookmarksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetBookmarksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
