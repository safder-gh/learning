import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormGenreComponent } from './form-genre.component';

describe('FormGenreComponent', () => {
  let component: FormGenreComponent;
  let fixture: ComponentFixture<FormGenreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormGenreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormGenreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
