import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ImageSliderComponent } from './image-slider/image-slider.component';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { SubscriptionComponent } from './subscription/subscription.component';
import { PaymentComponent } from './payment/payment.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {path:"",component:ImageSliderComponent},
  {path:"login",component:LoginComponent},
  {path:"register",component:RegisterComponent},
  {path:"home",component:ImageSliderComponent},
  {path: 'recommendation/:id', component: RecommendationComponent, canActivate:[AuthGuard]},
  {path: 'subs',component: SubscriptionComponent},
  {path: 'payment/:id',component: PaymentComponent, canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
