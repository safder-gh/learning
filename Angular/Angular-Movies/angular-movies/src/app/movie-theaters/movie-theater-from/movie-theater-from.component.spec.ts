import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieTheaterFromComponent } from './movie-theater-from.component';

describe('MovieTheaterFromComponent', () => {
  let component: MovieTheaterFromComponent;
  let fixture: ComponentFixture<MovieTheaterFromComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovieTheaterFromComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieTheaterFromComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
