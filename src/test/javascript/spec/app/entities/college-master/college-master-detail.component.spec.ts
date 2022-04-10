import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CollegeMasterDetailComponent } from 'app/entities/college-master/college-master-detail.component';
import { CollegeMaster } from 'app/shared/model/college-master.model';

describe('Component Tests', () => {
  describe('CollegeMaster Management Detail Component', () => {
    let comp: CollegeMasterDetailComponent;
    let fixture: ComponentFixture<CollegeMasterDetailComponent>;
    const route = ({ data: of({ collegeMaster: new CollegeMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CollegeMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CollegeMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollegeMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collegeMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collegeMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
