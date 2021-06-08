import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Person } from '../person/person.model';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { PersonService } from '../person/person.service';
import * as moment from 'moment';
import { cpf } from 'cpf-cnpj-validator'; 
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.less']
})
export class DialogComponent implements OnInit {
  creating = false;
  updating = false;
  deleting = false;

  form = new FormGroup({
    name: new FormControl("", [
      Validators.required,
      Validators.minLength(3),
      this.nameValidator,
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
    private snackBar: MatSnackBar,
  ) {
  }

  cpfValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value || cpf.isValid(control.value))
      return null
    else
      return {cpf: true}
  }

  dateValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value || (/^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/.test(control.value))) {
      const momentDate = moment(control.value, 'DD/MM/YYYY')
      if (momentDate.isValid() && momentDate.isBefore(moment()))
        return null;
    }
    return {date: true}
  }

  nameValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value || /^[a-zA-Z ]*$/.test(control.value))
      return null
    else
      return {name: true}
  }

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

    this.creating = true;
    this.personService.createPerson(newPerson)
      .subscribe(
        _ => {
          this.creating = false;
          this.dialogRef.close(true);
        },
        _ => {
          this.creating = false;
          this.snackBar.open("🤒 Houve um erro ao adicinar dados", "Ok")
        })
  }

  update() {
    const updatedPerson = this.getFormPerson()
    updatedPerson.id = this.personId

    this.updating = true;
    this.personService.updatePerson(updatedPerson)
      .subscribe(
        _ => {
          this.updating = false;
          this.dialogRef.close(true);
        },
        _ => {
          this.updating = false;
          this.snackBar.open("🤒 Houve um erro ao salvar os dados", "Ok")
        }
    )
  }

  delete() {
    this.deleting = true
    this.personService.deletePerson(this.personId)
      .subscribe(
        _ => {
          this.deleting = false
          this.dialogRef.close(true)
        },
        _ => {
          this.deleting = false
          this.snackBar.open("🤒 Houve um erro ao excluir os dados", "Ok")
        })
  }
}
