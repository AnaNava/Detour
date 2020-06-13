import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatMenuModule,
  MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatTabsModule,
  MatFormFieldModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatTableModule,
  MatDialogModule,
  MatSidenavModule

} from '@angular/material';

import {MatGridListModule} from '@angular/material/grid-list';
 

@NgModule({
  imports: [
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatTabsModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatDialogModule

  ],
  exports: [
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatTabsModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatDialogModule,
    MatSidenavModule,
    MatSidenavModule

  ]
})
export class MaterialModule {}