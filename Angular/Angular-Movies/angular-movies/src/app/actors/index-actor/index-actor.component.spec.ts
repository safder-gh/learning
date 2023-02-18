import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexActorComponent } from './index-actor.component';

describe('IndexActorComponent', () => {
  let component: IndexActorComponent;
  let fixture: ComponentFixture<IndexActorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndexActorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexActorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
