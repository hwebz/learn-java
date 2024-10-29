import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { OrderComponent } from './components/order/order.component';
import { OrderConfirmationComponent } from './components/order-confirmation/order-confirmation.component';
import { AuthGuardFn } from './services/auth.guard';
import { AuthorizedGuardFn } from './services/authorized.guard';
import { UpdateUserComponent } from './components/update-user/update-user.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [AuthorizedGuardFn] },
  { path: 'register', component: RegisterComponent, canActivate: [AuthorizedGuardFn] },
  {
    path: '',
    canActivate: [AuthGuardFn],
    children: [
      { path: 'products/:id', component: ProductDetailComponent },
      { path: 'orders', component: OrderComponent },
      { path: 'orders/:id', component: OrderConfirmationComponent },
      { path: 'update-user', component: UpdateUserComponent },
      { path: '', component: HomeComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
