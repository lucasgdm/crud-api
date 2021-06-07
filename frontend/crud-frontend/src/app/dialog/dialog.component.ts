import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Person } from '../person/person.model';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { PersonService } from '../person/person.service';
import * as moment from 'moment';
import { cpf } from 'cpf-cnpj-validator'; 

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.less']
})
export class DialogComponent implements OnInit {
  form = new FormGroup({
    name: new FormControl("", [
      Validators.required,
      Validators.minLength(5),
    ]),
    email: new FormControl("", [
      Validators.required,
      Validators.email,
    ]),
    taxpayerId: new FormControl("", [
      Validators.required,
      this.cpfValidator,
    ]),
    birthday: new FormControl("", [
      Validators.required,
      this.dateValidator,
    ]),
  })

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public personId: string,
    private personService: PersonService,
  ) {
  }

  cpfValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value || cpf.isValid(control.value))
      return null
    else
      return {cpf: true}
  }

  dateValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value || (/[0-9]{2}\/[0-9]{2}\/[0-9]{4}/g.test(control.value) && moment(control.value, 'DD/MM/YYYY').isValid()))
      return null;
    else
      return {date: true}
  };

  hasError(field: string, error: string) {
    return this.form.get(field)?.errors
      && this.form.get(field)?.touched
      && this.form.get(field)?.hasError(error)
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    if (this.personId) {
      this.fetchPerson()
    }
  }

  fetchPerson() {
    this.personService.getPerson(this.personId)
      .subscribe(person => {
        this.form.setValue({
          name: person.name,
          email: person.email,
          taxpayerId: person.taxpayerId,
          birthday: moment(person.birthday).format('DD/MM/YYYY'),
        })
      })
  }

  getFormPerson(): Person {
    const {name, email, taxpayerId, birthday} = this.form.value

    return {
      name,
      email,
      taxpayerId: taxpayerId.replace(/[^0-9]/g, ''),
      birthday: moment(birthday, 'DD/MM/YYYY').format('YYYY-MM-DD'),
    } as Person
  }

  create() {
    const newPerson = this.getFormPerson()

    this.personService.createPerson(newPerson)
      .subscribe(_ => {
        this.dialogRef.close(true);
      })
  }

  update() {
    const updatedPerson = this.getFormPerson()
    updatedPerson.id = this.personId

    this.personService.updatePerson(updatedPerson)
      .subscribe(_ => {
        this.dialogRef.close(true);
      })
  }

  delete() {
    this.personService.deletePerson(this.personId)
      .subscribe(_ => {
        this.dialogRef.close(true);
      })
  }
}
