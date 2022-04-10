import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubSubjectMasterDetailComponent } from 'app/entities/sub-subject-master/sub-subject-master-detail.component';
import { SubSubjectMaster } from 'app/shared/model/sub-subject-master.model';

describe('Component Tests', () => {
  describe('SubSubjectMaster Management Detail Component', () => {
    let comp: SubSubjectMasterDetailComponent;
    let fixture: ComponentFixture<SubSubjectMasterDetailComponent>;
    const route = ({ data: of({ subSubjectMaster: new SubSubjectMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubSubjectMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubSubjectMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubSubjectMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subSubjectMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subSubjectMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
