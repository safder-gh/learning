import { Component, OnInit, Output,Input, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { actorCreationDTO, actorDTO } from '../actors.model';

@Component({
  selector: 'app-form-actor',
  templateUrl: './form-actor.component.html',
  styleUrls: ['./form-actor.component.css']
})
export class FormActorComponent implements OnInit {
  constructor(private formBuilder:FormBuilder){}
  form!:FormGroup
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name:[
        '',{
          validators:[Validators.required]
        }
      ],
      dateOfBirth:'',
      picture:'',
      biography:'',
    })

    if(this.model !== undefined){
      this.form.patchValue(this.model)
    }
  }
  @Input()
  model!:actorDTO
  @Output()
  onSaveChanges=new EventEmitter<actorCreationDTO>()
  onImageSelected(image){
    this.form.get('picture')!.setValue(image)
  }
  changeMarkdown(content){
    this.form.get('biography')!.setValue(content)
  }
saveChanges(){
  this.onSaveChanges.emit(this.form.value)
}
}
