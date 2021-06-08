import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';
import { Person } from './person/person.model';
import { PersonService } from './person/person.service';
import * as moment from 'moment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'crud-frontend';

  displayedColumns: string[] = ['taxpayerId', 'name', 'email', 'birthday']
  persons: Person[] = []

  constructor(
    private personService: PersonService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
  ) {}

  ngOnInit() {
    this.getPeople()
  }

  clickedRow(row: Person) {
    this.openDialog(row)
  }

  formatDate(date: string) {
    return moment(date).format('DD/MM/YYYY')
  }

  getPeople() {
    this.personService
      .getPeople()
      .subscribe(
        persons => this.persons = persons,
        _ => this.snackBar.open("🤒 Houve um erro ao carregar os dados", "Ok")
      )
  }

  create() {
    this.openDialog(undefined)
  }

  openDialog(person: Person | undefined): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '600px',
      data: person?.id
    })

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.getPeople()
      }
    })
  }
}
