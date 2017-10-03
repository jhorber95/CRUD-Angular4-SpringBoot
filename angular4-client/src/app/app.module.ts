import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CreateCustomerComponent } from './create-customer/create-customer.component';

import { AppRoutingModule } from './app-routing.module';
import { CustomersComponent } from './customers/customers.component';
import { CustomerDetailsComponent } from './customer-details/customer-details.component';
import { DataService } from './data.service';

@NgModule({
  declarations: [
    AppComponent,
    CreateCustomerComponent,
    CustomersComponent,
    CustomerDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
