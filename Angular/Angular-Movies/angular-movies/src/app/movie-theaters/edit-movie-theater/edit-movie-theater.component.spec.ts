import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMovieTheaterComponent } from './edit-movie-theater.component';

describe('EditMovieTheaterComponent', () => {
  let component: EditMovieTheaterComponent;
  let fixture: ComponentFixture<EditMovieTheaterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditMovieTheaterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditMovieTheaterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
