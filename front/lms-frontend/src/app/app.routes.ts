import {Routes} from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginGuard } from './core/guards/login.guard';
import { DashboardComponent } from './shared/components/dashboard/dashboard.component';
import {PageNotFoundComponent} from './shared/components/page-not-found/page-not-found.component';
import { LibrarySearchPageComponent } from './pages/library/library-search-page/library-search-page.component';
import { LibraryCreatePageComponent } from './pages/library/library-create-page/library-create-page.component';
import { LibraryEditPageComponent } from './pages/library/library-edit-page/library-edit-page.component';
import { BookSearchPageComponent } from './pages/book/book-search-page/book-search-page.component';
import { BookCreatePageComponent } from './pages/book/book-create-page/book-create-page.component';
import { BookEditPageComponent } from './pages/book/book-edit-page/book-edit-page.component';
import { AuthorSearchPageComponent } from './pages/author/author-search-page/author-search-page.component';
import { AuthorCreatePageComponent } from './pages/author/author-create-page/author-create-page.component';
import { AuthorEditPageComponent } from './pages/author/author-edit-page/author-edit-page.component';
import { PublisherSearchPageComponent } from './pages/publisher/publisher-search-page/publisher-search-page.component';
import { PublisherCreatePageComponent } from './pages/publisher/publisher-create-page/publisher-create-page.component';
import { PublisherEditPageComponent } from './pages/publisher/publisher-edit-page/publisher-edit-page.component';
import { CategorySearchPageComponent } from './pages/category/category-search-page/category-search-page.component';
import { CategoryCreatePageComponent } from './pages/category/category-create-page/category-create-page.component';
import { CategoryEditPageComponent } from './pages/category/category-edit-page/category-edit-page.component';
import { CheckoutSearchPageComponent } from './pages/checkout/checkout-search-page/checkout-search-page.component';
import { CheckoutCreatePageComponent } from './pages/checkout/checkout-create-page/checkout-create-page.component';
import { CheckoutEditPageComponent } from './pages/checkout/checkout-edit-page/checkout-edit-page.component';
import { FineSearchPageComponent } from './pages/fine/fine-search-page/fine-search-page.component';
import { FineCreatePageComponent } from './pages/fine/fine-create-page/fine-create-page.component';
import { FineEditPageComponent } from './pages/fine/fine-edit-page/fine-edit-page.component';
import { UserSearchPageComponent } from './pages/user/user-search-page/user-search-page.component';
import { UserCreatePageComponent } from './pages/user/user-create-page/user-create-page.component';
import { UserEditPageComponent } from './pages/user/user-edit-page/user-edit-page.component';
import { ReservationSearchPageComponent } from './pages/reservation/reservation-search-page/reservation-search-page.component';
import { ReservationCreatePageComponent } from './pages/reservation/reservation-create-page/reservation-create-page.component';
import { ReservationEditPageComponent } from './pages/reservation/reservation-edit-page/reservation-edit-page.component';

export const routes: Routes = [
	{
		path: 'login',
		component: LoginComponent,
		canActivate: [LoginGuard]
	},
	{
		path: 'library',
		component: LibrarySearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'library.title', parent: '', permissions: ['can_view_library'] },
	},
	{
		path: 'library/create',
		component: LibraryCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'library', permissions: ['can_create_library'] },
	},
	{
		path: 'library/update/:id',
		component: LibraryEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'library', permissions: ['can_update_library'] },
	},
	{
		path: 'book',
		component: BookSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'book.title', parent: '', permissions: ['can_view_book'] },
	},
	{
		path: 'book/create',
		component: BookCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'book', permissions: ['can_create_book'] },
	},
	{
		path: 'book/update/:id',
		component: BookEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'book', permissions: ['can_update_book'] },
	},
	{
		path: 'author',
		component: AuthorSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'author.title', parent: '', permissions: ['can_view_author'] },
	},
	{
		path: 'author/create',
		component: AuthorCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'author', permissions: ['can_create_author'] },
	},
	{
		path: 'author/update/:id',
		component: AuthorEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'author', permissions: ['can_update_author'] },
	},
	{
		path: 'publisher',
		component: PublisherSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'publisher.title', parent: '', permissions: ['can_view_publisher'] },
	},
	{
		path: 'publisher/create',
		component: PublisherCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'publisher', permissions: ['can_create_publisher'] },
	},
	{
		path: 'publisher/update/:id',
		component: PublisherEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'publisher', permissions: ['can_update_publisher'] },
	},
	{
		path: 'category',
		component: CategorySearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'category.title', parent: '', permissions: ['can_view_category'] },
	},
	{
		path: 'category/create',
		component: CategoryCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'category', permissions: ['can_create_category'] },
	},
	{
		path: 'category/update/:id',
		component: CategoryEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'category', permissions: ['can_update_category'] },
	},
	{
		path: 'checkout',
		component: CheckoutSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'checkout.title', parent: '', permissions: ['can_view_checkout'] },
	},
	{
		path: 'checkout/create',
		component: CheckoutCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'checkout', permissions: ['can_create_checkout'] },
	},
	{
		path: 'checkout/update/:id',
		component: CheckoutEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'checkout', permissions: ['can_update_checkout'] },
	},
	{
		path: 'fine',
		component: FineSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'fine.title', parent: '', permissions: ['can_view_fine'] },
	},
	{
		path: 'fine/create',
		component: FineCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'fine', permissions: ['can_create_fine'] },
	},
	{
		path: 'fine/update/:id',
		component: FineEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'fine', permissions: ['can_update_fine'] },
	},
	{
		path: 'user',
		component: UserSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'user.title', parent: '', permissions: ['can_view_user'] },
	},
	{
		path: 'user/create',
		component: UserCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'user', permissions: ['can_create_user'] },
	},
	{
		path: 'user/update/:id',
		component: UserEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'user', permissions: ['can_update_user'] },
	},
	{
		path: 'reservation',
		component: ReservationSearchPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'reservation.title', parent: '', permissions: ['can_view_reservation'] },
	},
	{
		path: 'reservation/create',
		component: ReservationCreatePageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'create', parent: 'reservation', permissions: ['can_create_reservation'] },
	},
	{
		path: 'reservation/update/:id',
		component: ReservationEditPageComponent,
		canActivate: [AuthGuard],
		data: { breadcrumb: 'update', parent: 'reservation', permissions: ['can_update_reservation'] },
	},
	{ path: 'pageNotFound', component: PageNotFoundComponent, canActivate: [AuthGuard] },
	{ path: '', component: DashboardComponent, canActivate: [AuthGuard] },
	{ path: '**', redirectTo: 'pageNotFound' }
];
