import React, {useState} from 'react'
import ReactQuill from "react-quill";

import 'react-quill/dist/quill.snow.css';

const DanceRichEditor = ({value, setValue}) => {

    const modules = {
        toolbar: [
            [{ 'header': '1'}, {'header': '2'}, { 'font': [] }],
            [{size: []}],
            ['bold', 'italic', 'underline', 'strike', 'blockquote'],
            [{'list': 'ordered'}, {'list': 'bullet'},
                {'indent': '-1'}, {'indent': '+1'}],
            ['link', 'image', 'video'],
            ['clean']
        ],
        clipboard: {
            matchVisual: false,
        }
    };
    const formats = [
        'header', 'font', 'size',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image', 'video'
    ];
   
    return (
        <ReactQuill theme="snow" value={value} onChange={setValue} />
    )
}
export default DanceRichEditor;