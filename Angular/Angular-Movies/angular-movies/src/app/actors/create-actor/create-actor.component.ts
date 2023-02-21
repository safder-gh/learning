import { Component } from '@angular/core';
import { actorCreationDTO, actorDTO } from '../actors.model';

@Component({
  selector: 'app-create-actor',
  templateUrl: './create-actor.component.html',
  styleUrls: ['./create-actor.component.css']
})
export class CreateActorComponent {
  model:actorDTO ={
    name:'',
    dateOfBirth:new Date(),
    picture:'',
    biography:''
  }

  saveChanges(actorCreationDTO:actorCreationDTO){
    console.log(actorCreationDTO)
  }
}
