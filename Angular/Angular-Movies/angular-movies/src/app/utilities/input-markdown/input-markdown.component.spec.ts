import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMarkdowwnComponent } from './input-markdowwn.component';

describe('InputMarkdowwnComponent', () => {
  let component: InputMarkdowwnComponent;
  let fixture: ComponentFixture<InputMarkdowwnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputMarkdowwnComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputMarkdowwnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
