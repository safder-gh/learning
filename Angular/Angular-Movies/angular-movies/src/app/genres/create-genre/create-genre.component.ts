import { Component , OnInit } from '@angular/core'
import {Router} from '@angular/router'
import {FormBuilder,FormGroup ,Validators} from '@angular/forms'
import { firstLetterUppercase } from '../../validators/firstLetterUppercase';


@Component({
  selector: 'app-create-genre',
  templateUrl: './create-genre.component.html',
  styleUrls: ['./create-genre.component.css']
})
export class CreateGenreComponent implements OnInit{
  constructor(private router:Router,private formBuilder:FormBuilder){}
  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name:['',{validators:[
        Validators.required,
        Validators.minLength(3),
        firstLetterUppercase()
      ]}],
    })
  }
  form!: FormGroup
saveChanges(){
  this.router.navigate(['/genres'])
}
getErrorMessageFieldName(){
  const field = this.form.get('name')
  if(field && field.hasError('required')){
    return 'The name field is required!'
  }else if(field && field.hasError('minlength')){
    return 'Minimum length is 3!'
  }
  if(field && field.hasError('firstLetterUppercase')){
    return field.getError('firstLetterUppercase').message
  }
  return ''
}
}
