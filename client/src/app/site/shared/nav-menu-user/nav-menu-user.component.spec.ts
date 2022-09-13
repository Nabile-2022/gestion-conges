import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavMenuUserComponent } from './nav-menu-user.component';

describe('NavMenuUserComponent', () => {
  let component: NavMenuUserComponent;
  let fixture: ComponentFixture<NavMenuUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavMenuUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavMenuUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
